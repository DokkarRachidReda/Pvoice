cd /home/pepe/Documents
inotifywait -e modify /home/pepe/Documents
yourfilenames=`ls *.txt`
for file in $yourfilenames
do
	cp $file  ${file%.txt}.scad
	openscad -o ${file%.txt}.stl ${file%.txt}.scad
	meshlabserver -i ${file%.txt}.stl -o ${file%.txt}.obj
done

./script2.sh

kill -9 $$

#exit 0

