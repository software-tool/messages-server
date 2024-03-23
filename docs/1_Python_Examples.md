# Python Examples

Example script to trigger a message:

```python
#!/usr/bin/python

import requests
from urllib.parse import quote

sendMessage("Hello");

def sendMessage(title, text)
  # Step: Make a POST request to a certain URL
  url = "http://localhost/info?location=tmp&title="+ quote(title) +"&text=" + quote(text)
  payload = {}
  response = requests.post(url, data=payload)

  # Check the response status
  if response.status_code == 200:
      #print("POST request successful")
      pass
  else:
      print(f"Error: {response.status_code} - {response.text}")
      
```