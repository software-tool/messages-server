# Curl Examples

Curl command examples to get/post data to the server.

## Post Messages

curl -X POST "localhost/info?location=debug&title=Server1_State&text=Running"

## Read Messages

curl -X GET http://localhost/list?location=debug
