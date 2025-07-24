# Overview

This project utilizes lifecycle scripts to automate tasks during the development container's lifecycle. These scripts help in setting up the environment, managing updates, and handling container events efficiently.
For more information on lifecycle scripts, refer to the [official documentation](https://containers.dev/implementors/json_reference/#lifecycle-scripts).

## Lifecycle Scripts

The following lifecycle scripts are defined in the `.devcontainer/lifecycle` directory:

- **onCreateCommand.sh**: This script runs after the container is created. It is used to set up the development environment, such as installing dependencies.

- **updateContentCommand.sh**: This script runs after the container's content is updated. It is useful for applying updates or patches to the environment.

- **postStartCommand.sh**: This script runs every time the container starts. It is ideal for starting services or running initialization tasks.

- **postAttachCommand.sh**: This script runs after a user connects to the container. It can be used to display welcome messages or run user-specific commands.

- **postStopCommand.sh**: This script runs after the container stops. It is used to clean up resources or save state.

## Usage

To use these lifecycle scripts, ensure they are executable and properly configured in your `devcontainer.json`. Each script should contain the necessary commands to perform its designated task.

For example, you might have the following commands in your scripts:

- `onCreateCommand.sh`: `npm install`
- `updateContentCommand.sh`: `npm update`
- `postStartCommand.sh`: `npm run start`
- `postAttachCommand.sh`: `echo 'Welcome to your dev container!'`
- `postStopCommand.sh`: `echo 'Container stopped'`

Refer to the individual script files for specific implementations and further details.