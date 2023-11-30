package signlanguageDetector;


import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.datavec.api.io.filters.RandomPathFilter;
import org.datavec.api.records.metadata.RecordMetaDataImageURI;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.objdetect.ObjectDetectionRecordReader;
import org.datavec.image.recordreader.objdetect.impl.VocLabelProvider;
import org.deeplearning4j.core.storage.StatsStorage;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.ConvolutionMode;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.objdetect.Yolo2OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.transferlearning.FineTuneConfiguration;
import org.deeplearning4j.nn.transferlearning.TransferLearning;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.model.stats.StatsListener;
import org.deeplearning4j.ui.model.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.deeplearning4j.zoo.model.TinyYOLO;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.RmsProp;
import utils.YoloModel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import org.datavec.api.io.filters.RandomPathFilter;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.deeplearning4j.nn.weights.WeightInit;




// from here is death for I am the end of things


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;






public class YoloTrainer {

    private static final Logger log = LoggerFactory.getLogger(YoloTrainer.class);

    //  640 x 480 webcam resolution

    private static final int INPUT_WIDTH =  640;
    private static final int INPUT_HEIGHT = 480;
    private static final int CHANNELS = 3;

    private static final int GRID_WIDTH = 13;
    private static final int GRID_HEIGHT = 13;
    private static final int CLASSES_NUMBER = 1;
    private static final int BOXES_NUMBER = 5;
    private static final double[][] PRIOR_BOXES = {{1.5, 1.5}, {2, 2}, {3,3}, {3.5, 8}, {4, 9}};//anchors boxes

    private static final int BATCH_SIZE = 4;
    private static final int EPOCHS = 50;
    private static final double LEARNIGN_RATE = 0.0001;
    private static final int SEED = 1234;

    /*parent Dataset folder "DATA_DIR" contains two subfolder "images" and "annotations" */
    private static final String DATA_DIR = "C:\\Users\\CodeZ\\IdeaProjects\\Testcase03\\src\\Dataset";

    /* Yolo loss function prameters for more info
    https://stats.stackexchange.com/questions/287486/yolo-loss-function-explanation*/
    private static final double LAMDBA_COORD = 1.0;
    private static final double LAMDBA_NO_OBJECT = 0.5;


    public static void main(String[] args) throws IOException, InterruptedException {

        RandomPathFilter pathFilter = new RandomPathFilter(rng) {
            @Override
            protected boolean accept(String name) {
                name = name.replace("/images/", "/annotations/").replace(".jpg", ".xml");
                //System.out.println("Name " + name);
                try {
                    return new File(new URI(name)).exists();
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        InputSplit[] data = new FileSplit(imageDir, NativeImageLoader.ALLOWED_FORMATS, rng).sample(pathFilter, .8, 0.2);
        InputSplit trainData = data[0];
        InputSplit testData = data[1];

        ObjectDetectionRecordReader recordReaderTrain = new ObjectDetectionRecordReader(INPUT_HEIGHT, INPUT_WIDTH, CHANNELS,
                GRID_HEIGHT, GRID_WIDTH, new VocLabelProvider(DATA_DIR));
        recordReaderTrain.initialize(trainData);

        ObjectDetectionRecordReader recordReaderTest = new ObjectDetectionRecordReader(INPUT_HEIGHT, INPUT_WIDTH, CHANNELS,
                GRID_HEIGHT, GRID_WIDTH, new VocLabelProvider(DATA_DIR));
        recordReaderTest.initialize(testData);

        RecordReaderDataSetIterator train = new RecordReaderDataSetIterator(recordReaderTrain, BATCH_SIZE, 1, 1, true);
        train.setPreProcessor(new ImagePreProcessingScaler(0, 1));

        RecordReaderDataSetIterator test = new RecordReaderDataSetIterator(recordReaderTest, 1, 1, 1, true);
        test.setPreProcessor(new ImagePreProcessingScaler(0, 1));
    }

}
