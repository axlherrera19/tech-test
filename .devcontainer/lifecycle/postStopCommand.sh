#!/bin/bash
# This script runs after the container stops. It is used to clean up resources or save state.

echo "🤦Cleaning up resources..."
# Add your cleanup commands here

dapr stop

echo "Container stopped."