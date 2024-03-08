FROM node:20.9-alpine as build

WORKDIR /app
COPY ./package.json ./yarn.lock ./
COPY ./backoffice/package.json  ./backoffice/
COPY ./common/package.json  ./common/
RUN yarn install

COPY ./backoffice ./backoffice
COPY ./common/src/main/node/ ./common/src/main/node/
RUN yarn build:backoffice

FROM node:20.9-alpine

WORKDIR /app
COPY --from=build /app/package.json /app/yarn.lock ./
COPY --from=build /app/backoffice/package.json /app/backoffice/package.json
COPY --from=build /app/common/package.json /app/common/package.json
COPY --from=build /app/common/src/main/node /app/common/src/main/node
COPY --from=build /app/backoffice/.next /app/backoffice/.next
RUN yarn install --production

EXPOSE 3000
LABEL org.opencontainers.image.source https://github.com/Aredli16/RegistriX
MAINTAINER Aredli16

CMD ["yarn", "start:backoffice"]
