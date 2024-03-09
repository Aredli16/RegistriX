
# RegistriX

RegistriX is an application allowing an organization to manage its registrations directly online. 100% configurable, this application will allow you to adapt to any organization according to your specifications


## Features

- Complete and customizable authentication
- User Registration
- Admin interface


## Installation

This project uses Java 21, NodeJS 20, Yarn 1.22 and Docker
Once these dependencies are installed, run the commands:

```bash
  yarn install
  yarn build
```

Once the commands are played, the NodeJS dependencies are installed and the Docker images of the backend modules are installed locally on your Docker instance.

If you do not want to build the images locally, you can directly run the `yarn start` command which will pull the Docker images that are hosted on GitHub
## Run Locally

Whether you have built your project or not, you can launch the project with the command:

```bash
  yarn start
```

If you built the project with `yarn build` then the images will be taken from your Docker instance. Otherwise the images will be found from GitHub

You can now access http://localhost:3000 which corresponds to the administrator interface
You can also access http://localhost:3001 which corresponds to the interface for clients.

By default, Keycloak admin credentials for the associated realm are created:
- username: `admin`
- password: `admin`

User identifiers are also created:
- username: `user`
- password: `user`

## Run Locally (Dev)

To launch the project in development mode and thus have Fast Refresh functionality (only for interfaces. Fast Refresh cannot be used for backend modules), run the command:

```bash
  yarn dev
```

You now have access to the same URL and user as when you launched `yarn start` but you can now admire the changes while coding without having to restart your application. Happy Coding !


## Running Tests

To run tests, run the following command

```bash
  yarn test
```


## Deployment

The deployment uses Docker to publish an image that represents an instance of each application. To deploy the application run the command:

```bash
  yarn deploy
```

Le pipeline GitHub s'occupe déjà de déployer les images à chaque commit.
## Authors

- [@Aredli16](https://www.github.com/Aredli16)


## Support

For support or a quote request, please contact me at corentin.lempereur16@gmail.com

