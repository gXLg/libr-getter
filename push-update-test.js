const fs = require("fs");

const n = [null, null];
const prop = fs.readFileSync("./gradle.properties", "utf-8");
for (const line of prop.split("\n").map(l => l.trim())) {
    if (line.length == 0 || line[0] == "#") continue;
    const [p, v] = line.split("=");
    if (p == "archives_base_name") n[0] = v;
    if (p == "mod_version") n[1] = v;
}
const jar = "./build/libs/" + n.join("-") + ".jar";
const modrinth = "C:/Users/Dimi-/AppData/Roaming/ModrinthApp/profiles";
for (const dir of fs.readdirSync(modrinth, { withFileTypes: true }).filter(e => e.isDirectory()).map(d => d.name)) {
    if (dir == "1.20.1") continue; // my private installation
    fs.copyFileSync(jar, modrinth + "/" + dir + "/mods/librgetter-test.jar");
}