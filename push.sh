#! /bin/bash
#push to github

./pull.sh

git add -A 
git commit -m "maj"
git push origin master
