package org.firstinspires.ftc.teamcode.OtherProjects;

import android.annotation.SuppressLint;
import android.text.style.TabStopSpan;

import org.firstinspires.ftc.teamcode.FullBase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.function.Function;

public class AutonomousBuilder{
    String name;
    Path path_to_auto;
    DataOutputStream out;

    public static final String PACKAGE =
            "package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;\n\n";
    public static final String IMPORTS =
            //"import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.*;\n" +
            "import com.qualcomm.robotcore.eventloop.opmode.Autonomous;\n"   +
            "import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;\n" +
            "import org.firstinspires.ftc.teamcode.FullBase;\n";
    public static final String PATH_TO_AUTOS = "../FreightFrenzyOpMode/Autonomous/";

    public interface Task{
//        @SuppressLint("NewApi")
        void execute(FullBase base);
    }
    public static class TimerTask implements Task{
        Consumer<FullBase> function;
        String[] components;
        static DataOutputStream out;

        public static void init(DataOutputStream out){
            EncoderTask.out = out;
        }
        public TimerTask(Consumer<FullBase> consumer, String... components){
            function = consumer;
            this.components = components;
        }
//        @SuppressLint("NewApi")
        @Override
        public void execute(FullBase base){
            long begin = System.nanoTime();
            function.accept(base);
            long end = System.nanoTime();
            try {
                for (String s : components) {
                    out.writeBytes(String.format("\t\t%s.setPowerInAuto(1);\n", s));
                }
                out.writeBytes(String.format("\t\ttry{Thread.sleep(%d);}catch(Exception e){}\n",
                        end - begin));
                for(String s : components){
                    out.writeBytes(String.format("\t\t%s.setPowerInAuto(0);\n", s));
                }
//                out.writeBytes();("\t\ttry{Thread.sleep(500);}catch(Exception e){}");
            } catch(Exception e){}
        }
    }

    public static EncoderTask.ChildComponent[] ChildComponents(String... components){
        EncoderTask.ChildComponent[] ret = new EncoderTask.ChildComponent[components.length];

        for(int i = 0; i < components.length; ++i){
            ret[i] = new EncoderTask.ChildComponent(components[i]);
        }
        return ret;
    }
    public static EncoderTask.MainComponent[] MainComponents(int[][] encoders, String... components){
        EncoderTask.MainComponent[] ret = new EncoderTask.MainComponent[components.length];

        for (int i = 0; i < components.length; ++i) {
            ret[i] = new EncoderTask.MainComponent(components[i],encoders[i]);
        }
        return ret;
    }

    public static int[][] encoderArray(int[]... encoders){
        return encoders;
    }
    public static int[] encoders(int... encoders){
        return encoders;
    }

    public static class EncoderTask implements Task{
        static DataOutputStream out;

        public static void init(DataOutputStream out){
            EncoderTask.out = out;
        }
        public static class RetType{
            public ChildComponent[]  childComponents;
            public MainComponent[]   mainComponents;
            public RetType(){}

            public RetType(ChildComponent[] ccs, MainComponent[] mcs){
                childComponents = ccs;
                mainComponents  = mcs;
            }
        }
        public static class ChildComponent {
            public String component;
            public ChildComponent(){}

            public ChildComponent(String component){
                this.component = component;
            }
        }
        public static class MainComponent  {
            public int[] encoders;
            public String component;
            public MainComponent(){}

            public MainComponent(String component, int... encoders){
                this.component = component;
                this.encoders  = encoders;
            }
        }
        Function<FullBase, RetType> function;

        public EncoderTask(Function<FullBase, RetType> function){
            this.function = function;
        }

//        @SuppressLint("NewApi")
        @Override
        public void execute(FullBase base){
            RetType retType = function.apply(base);
            try{
                for(ChildComponent info: retType.childComponents){
                    out.writeBytes(String.format("\t\t%s.setPowerInAuto(1);\n", info.component));
                }
                for(MainComponent info: retType.mainComponents){
                    out.writeBytes(
                            String.format("\t\t%s.setTargetPosition(new int[]{", info.component));
                    out.writeInt(info.encoders[0]);
                    for(int i = 1; i < info.encoders.length; ++i){
                        out.writeBytes(String.format(",%d", info.encoders[i]));
                    }
                    out.writeBytes("});\n");
                }

                out.writeBytes(
                        String.format("\t\twhile(%s.isBusy()",
                                retType.mainComponents[0].component));
                for(int i = 1; i < retType.mainComponents.length; ++i){
                    out.writeBytes(
                            String.format(" || %s.isBusy()",
                                    retType.mainComponents[i].component));
                }
                out.writeBytes("){}\n");
                for(ChildComponent info: retType.childComponents){
                    out.writeBytes(
                            String.format("\t\t%s.setPowerInAuto(0);\n", info.component));
                }
//                out.writeBytes();("\t\ttry{Thread.sleep(500);}catch(Exception e){}\n");
            } catch(Exception e){}
        }
    }

    public Task[]     tasks;
    public String[]   taskDescriptions;

    public boolean createTask(int index, String description, Task task){
        if(index >= tasks.length || index < 0) return false;

        taskDescriptions[index] = description;
        tasks[index]            = task;
        return true;
    }
    //@SuppressLint("NewApi")
    public AutonomousBuilder(FullBase base, String name, int numTasks){
        try {
            base.getTelemetry().addLine("In AutoBuilder Ctor");
            base.getTelemetry().update();
            this.name = name;
            try {
                this.path_to_auto = Paths.get(PATH_TO_AUTOS + name + ".java");
            } catch (Exception e) {
                base.getTelemetry().addLine(e.getMessage());
                base.getTelemetry().update();
                try {
                    Thread.sleep(1000);
                } catch (Exception e2) {
                }
                System.exit(1);
            }
            this.tasks = new Task[numTasks];
            this.taskDescriptions = new String[numTasks];

            try {
                this.out = new DataOutputStream(
                        Files.newOutputStream(path_to_auto));
            } catch (Exception e) {
                base.getTelemetry().addLine(e.getMessage());
                base.getTelemetry().update();
                try {
                    Thread.sleep(1000);
                } catch (Exception e2) {
                }
                System.exit(1);
            }
            EncoderTask.init(out);
            TimerTask.init(out);
        }
        catch(Exception e){
            base.getTelemetry().addLine(e.getMessage());
            base.getTelemetry().update();
            try{Thread.sleep(1000);}catch(Exception ex){}
        }
    }
    public boolean createStartOfAuto(){
        try {
            out.writeBytes(PACKAGE);
            out.writeBytes(IMPORTS);
            out.writeBytes(
                    String.format(
                            "\n/************************\nGenerated Auto: %1$s\nMade by: " +
                            "FTC Team Direct Current 5893\n\n\"I don't even know what street " +
                            "Canada is on.\"\n- Al Capone\n\nC++ Forever!********" +
                            "****************/\n@Autonomous(name=\"%1$s\")\n" +
                            "public class %1$s extends LinearOpMode{\n"
                            , name));
            out.writeBytes(
                    "\tFullBase base;\n\n\t@Override public void runOpMode(){" +
                    "\n\t\tbase = new FullBase(telemetry, this, hardwareMap, false);\n\t\t" +
                    "base.init();\n\t\ttelemetry.addData(\"Status\", \"Initialized\");\n\t\t" +
                    "telemetry.update();\n\t\twaitForStart();\n");

        } catch (Exception e) { return false;}
        return true;
    }
    public void createEndOfAuto(){
        try{
            out.writeBytes("\t}\n}");
        } catch(Exception e){}
        finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
