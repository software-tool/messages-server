# Bash Examples

Use curl in bash to post a new message:

```bash
#!/bin/bash

curl -X POST "localhost/info?location=myinfo&title=Action_Done&text=Some_Details"
```