parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"
javadoc -version -author -sourcepath ../src -cp ../bin -d ../docs edu.rpi.cs.csci4963.su20.dzm.pacman edu.rpi.cs.csci4963.su20.dzm.pacman.game
