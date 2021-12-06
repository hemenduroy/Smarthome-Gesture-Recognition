from flask import Flask, request, redirect
import os
app = Flask(__name__)
app.config["VIDEO_UPLOADS"] = "/Users/hemendu/server535/videos/"

# Declare your first route
@app.route('/upload', methods=['GET', 'POST'])
def upload_video():
    if request.method == "POST":
        if request.files:
            for file in request.files:
                print(file)
                video = request.files[file]
                video.save(os.path.join(app.config["VIDEO_UPLOADS"], video.filename))
            return redirect(request.url)
    else:
        return "Upload server is online", 200


if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)
