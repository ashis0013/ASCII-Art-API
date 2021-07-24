# ASCII Art API

A simple API service built with Ktor to get ascii art of an image. This API takes request in this form

```json
{
  "img" : "image encoded in BASE64 string",
  "width" : "The width of ascii image. Length of each line"
}
```
The API response is a text for now.

If you run this python script

```python
import requests
import base64

f = open('lena.png','rb')
url = 'http://localhost:8080/ascii-art'
encoded_string = base64.b64encode(f.read())
myobj = {'img' : encoded_string, 'width' : '100'}
call = requests.post(url,data=myobj)
print(call.text)
```

Then the result would be
![ezgif com-gif-maker](https://user-images.githubusercontent.com/31564734/126866414-a48ebfa9-0128-449b-b106-8cc70d5c8dee.gif)

