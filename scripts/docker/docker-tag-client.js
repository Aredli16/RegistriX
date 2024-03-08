const { execSync } = require("node:child_process");

const imagePrefix =
  require("../../package.json").properties.docker.image.prefix;
const clientVersion = require("../../client/package.json").version;
execSync(
  `docker tag ${imagePrefix}/client ghcr.io/aredli16/client:${clientVersion}`,
);
