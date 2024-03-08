FROM node:20.9-alpine as build

WORKDIR /app
COPY ./package.json ./yarn.lock ./
COPY ./client/package.json  ./client/
COPY ./common/package.json  ./common/
RUN yarn install

COPY ./client ./client
COPY ./common/src/main/node/ ./common/src/main/node/
RUN yarn build:client

FROM node:20.9-alpine

WORKDIR /app
COPY --from=build /app/package.json /app/yarn.lock ./
COPY --from=build /app/client/package.json /app/client/package.json
COPY --from=build /app/common/package.json /app/common/package.json
COPY --from=build /app/common/src/main/node /app/common/src/main/node
COPY --from=build /app/client/.next /app/client/.next
RUN yarn install --production

EXPOSE 3000
LABEL org.opencontainers.image.source https://github.com/Aredli16/RegistriX
MAINTAINER Aredli16

CMD ["yarn", "start:client"]
