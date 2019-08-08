cd ../comp-build-backend/src/main/resources/
rm -rf static
rm *.json
rm *.html
rm *.ico
rm *.js
rm .css
mkdir -p static/static/js
cd ../../../../comp-build-frontend
npm run build
cp ./templates/index.html ../comp-build-backend/src/main/resources/static/
cp ./static/main.js ../comp-build-backend/src/main/resources/static/static/js
cp ./static/main.js ../../../comp_build_backend_heroku_upload/src/main/resources/static/static/js/
date