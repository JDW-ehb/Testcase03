About My Project: Sign Language Recognition with Object Detection
-------------
Introduction
-------------
This project is centered around sign language recognition using object detection. It integrates various technologies to create a system that can recognize sign language through a camera feed, then sending a tts in a discord channel. The main components include:

- OpenCV: Utilized for capturing video frames from a camera.
- DeepLearning4J (DL4J): An open-source, distributed deep learning library used for the core detection algorithms.
- LabelImg: An application for annotating objects in images, aiding in the training process.
- FreeTTS: A text-to-speech (TTS) engine for audio feedback.
- Java Discord API (JDA): Used for integrating a Discord bot into the project.

Despite the innovative approach, the project faced significant challenges, particularly with unsupported libraries and Maven dependencies, leading to extensive debugging efforts.

------------------------------------------
Interesting Discussion About the Libraries
- Sign Language Detector
   - Utilizes continuous video capture from a webcam.
   - Employs the YOLO model for detecting sign language in video frames.
   - Integrates a Discord bot that connects to voice channels for audio output.

- My AudioSendHandler
  - Manages the sending of audio data to a Discord channel.

- YoloTrainer
  - Responsible for training the YOLO model on our dataset.

 - Non-Max Suppression (NMS)
   - Used to resolve overlaps in object detection, ensuring accurate results.

 - YoloModel
   - A specialized model for detecting sign language using the YOLO algorithm.

 - Showcase
   - LabelImg: Demonstrating the annotation of images for training.

- YoloTrainer: A brief overview of how the model is trained.

- Discord Bot: Showcasing the bot logging into a server. Note: The TTS works in JVM, but there are issues with audio transmission to the bot.

--------------------------
Technology Recommendations
While OpenCV and DeepLearning4J are powerful, they can be complex and challenging to implement effectively. For those starting in this field, I recommend exploring YOLO versions 5-9 in Python, as they tend to be more user-friendly.

--------------
What I Learned
The importance and intricacies of debugging, especially in complex projects.
Navigating and resolving Maven dependency issues.
Developing and integrating a Discord bot into an application.



helpfull sources i used for this project: 
https://github.com/tahaemara/yolo-custom-object-detector/tree/master/java
https://www.youtube.com/watch?v=fjynQ9P2C08&t=389s
https://www.youtube.com/watch?v=NxOxcgbikJo
