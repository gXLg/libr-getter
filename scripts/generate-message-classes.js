const fs = require("fs");
const lang = JSON.parse(fs.readFileSync("src/main/resources/assets/librgetter/lang/en_us.json"));
const translationKeys = [...Object.keys(lang)];
const messageTypes = ["error", "feedback", "success", "warning", "partial"];
const genPath = "src/main/java/dev/gxlg/librgetter/utils/types/messages";

const template = `package dev.gxlg.librgetter.utils.types.messages.{type};

public class {name} extends Translatable{ctype}Message {
    // {lang}
    public {name}({args}) {
        super("{key}"{argNames});
    }
}
`;

for (const messageType of messageTypes) {
    const typePath = genPath + "/" + messageType;
    const ctype = messageType.slice(0, 1).toUpperCase() + messageType.slice(1);

    // collect all translation keys
    const remainingKeys = translationKeys.filter(key => key.startsWith("librgetter." + messageType + "."));
    // find those already present
    for (const file of fs.readdirSync(typePath)) {
        const fileContent = fs.readFileSync(typePath + "/" + file, "utf-8");
        let keyToRemove = -1;
        for (let i = 0; i < remainingKeys.length; i++) {
            const key = remainingKeys[i];
            if (fileContent.includes(`"${key}"`)) {
                keyToRemove = i;
                break;
            }
        }
        if (keyToRemove == -1) continue;
        remainingKeys.splice(keyToRemove, 1);
    }
    // generate from remaining
    for (const key of remainingKeys) {
        const name = "_" + key.split(".").slice(-1)[0];
        const argCount = lang[key].split("%s").length - 1;
        const args = [];
        const argNames = [];
        for (let i = 0; i < argCount; i++) {
            args.push("Object arg" + (i + 1));
            argNames.push("arg" + (i + 1));
        }
        fs.writeFileSync(
            typePath + "/" + name + ".java",
            template
                .replaceAll("{name}", name)
                .replaceAll("{type}", messageType)
                .replaceAll("{ctype}", ctype)
                .replaceAll("{lang}", lang[key])
                .replaceAll("{key}", key)
                .replaceAll("{args}", args.join(", "))
                .replaceAll("{argNames}", argNames.map(n => ", " + n).join(""))
        );
        console.log("Generated message class for", key);
    }
}
