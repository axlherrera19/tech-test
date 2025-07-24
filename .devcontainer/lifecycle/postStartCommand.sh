#!/bin/bash
# This script runs every time the container starts.
# Ideal for starting services or running initialization tasks.

# Example: Start a service
# service_name start

# Example: Run initialization tasks
# initialize_task_command

echo "Container has started. Running post-start tasks..."

# whoami
# cd /workspace
# dotnet workload update
# dotnet restore
# dotnet new tool-manifest --force
# dotnet tool install csharpier

# # Check if Dapr runtime binaries are present inside the container
# DAPR_DAPRD="$HOME/.dapr/bin/daprd"

# # Install Dapr runtime binaries if missing
# if [ ! -f "$DAPR_DAPRD" ]; then
#     echo "⬇️ Dapr runtime binaries not found inside container. Installing..."
#     dapr uninstall --all || true
#     dapr init --runtime-version 1.15.0
# else
#     echo "✅ Dapr runtime binaries already present inside container."
# fi

# if [ ! -f "$HOME/.dapr/completion.bash.inc" ]; then
#     dapr completion bash >~/.dapr/completion.bash.inc
#     printf "source '$HOME/.dapr/completion.bash.inc'" >>$HOME/.bash_profile
#     source $HOME/.bash_profile
# fi

# echo "🚀  Starting Dapr"

## Force git mode to merge instead of rebase
git config pull.rebase false
# ls -la /opt/devcontainer/m2
# ls -la /home/vscode/
# mkdir -pv /home/vscode/.m2
# ls -la /home/vscode/.m2
# cp /opt/devcontainer/m2/settings.xml /home/vscode/.m2/settings.xml
ls -la /home/vscode/.m2/
export M2_HOME=/home/vscode/.m2