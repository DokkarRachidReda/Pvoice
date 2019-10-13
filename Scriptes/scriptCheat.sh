cd /home/pepe/Documents/cheating
HOST='192.168.137.251'
USER='upload'
PASSWD='chocolatefries'

ftp -n -v $HOST << EOT
ascii
user $USER $PASSWD
prompt

get model.scad
openscad -o model.stl model.scad 
send model.stl

bye
EOT
