const fs = require("fs");

const path = "./src/main/resources/assets/librgetter/lang/%.json";
const langs = ["de_de", "en_us", "ru_ru", "ja_jp", "vi_vn", "es_es"];

for (const lang of langs) {
    const fpath = path.replace("%", lang);
    const json = JSON.parse(fs.readFileSync(fpath));
    fs.writeFileSync(fpath, JSON.stringify(json, Object.keys(json).sort(), 2));
}
