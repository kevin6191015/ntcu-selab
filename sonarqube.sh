sudo usermod -aG docker $USER
newgrp docker
sudo sysctl -w vm.max_map_count=262144
sudo sysctl -w fs.file-max=65536