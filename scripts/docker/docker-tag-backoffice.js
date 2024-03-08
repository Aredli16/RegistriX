const { execSync } = require("node:child_process");

const imagePrefix =
  require("../../package.json").properties.docker.image.prefix;
const clientVersion = require("../../backoffice/package.json").version;
execSync(
  `docker tag ${imagePrefix}/backoffice ghcr.io/aredli16/backoffice:${clientVersion}`,
);
