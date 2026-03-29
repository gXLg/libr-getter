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
const home = "/home/dmitry/.dmitry-launcher/profiles";
for (const dir of fs.readdirSync(home, { withFileTypes: true }).filter(e => e.isDirectory()).map(d => d.name)) {
    if (!dir.startsWith("Test ")) continue;
    fs.mkdirSync(home + "/" + dir + "/mods", { "recursive": true });
    fs.copyFileSync(jar, home + "/" + dir + "/mods/librgetter-test.jar");
    console.log("Installed on", dir);
}