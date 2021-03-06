package org.firstinspires.ftc.teamcode;
//https://www.youtube.com/watch?v=5Ge_9rwQTqA;
//https://www.youtube.com/watch?v=Ne09Y72zW_Y;
//https://www.youtube.com/watch?v=RMYvH2SHUaM;
//https://pdocs.kauailabs.com/navx-micro/examples/field-oriented-drive/;
//Navx gGyro;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum Test", group="Iterative Opmode")

public class mecanum extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left1Drive = null;
    private DcMotor right1Drive = null;
    private DcMotor left2Drive =null;
    private DcMotor right2Drive=null;
    private double drive;
    private double strafe;
    private double turn;
    private boolean killLeft1=false;
    private boolean killLeft2=false;
    private boolean killRight1=false;
    private boolean killRight2=false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        left1Drive  = hardwareMap.get(DcMotor.class, "left_drive1");
        right1Drive = hardwareMap.get(DcMotor.class, "right_drive1");
        left2Drive= hardwareMap.get(DcMotor.class,"left_drive2");
        right2Drive = hardwareMap.get(DcMotor.class, "right_drive2");

        left1Drive.setDirection(DcMotor.Direction.FORWARD);
        left2Drive.setDirection(DcMotor.Direction.FORWARD);
        right1Drive.setDirection(DcMotor.Direction.REVERSE);
        right2Drive.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left1Power;
        double left2Power;
        double right1Power;
        double right2Power;

        drive = -gamepad1.left_stick_y;
        strafe= gamepad1.left_stick_x;
        turn  =  gamepad1.right_stick_x;

        if(killLeft1) {
            left1Power=0;
        }
        else{
            left1Power = Range.clip(drive + turn + strafe, -1.0, 1.0);
        }
        if(killLeft2) {
            left2Power=0;
        }
        else{
            left2Power = Range.clip(drive + turn + strafe, -1.0, 1.0);
        }
        if(killRight1) {
            right1Power=0;
        }
        else{
            right1Power = Range.clip(drive - turn + strafe, -1.0, 1.0);
        }
        if(killRight2) {
            right2Power=0;
        }
        else{
            right2Power = Range.clip(drive - turn + strafe, -1.0, 1.0);
        }

        left1Drive.setPower(left1Power);
        left2Drive.setPower(left2Power);
        right1Drive.setPower(right1Power);
        right2Drive.setPower(right2Power);

        //Strafing Effects;

        if(strafe<0){
            if(drive<0){
                killLeft2=true;
                left1Drive.setDirection(DcMotor.Direction.REVERSE);
                right2Drive.setDirection(DcMotor.Direction.FORWARD);
                killRight1=true;
            }

            if(drive>0){
                killLeft1=true;
                left2Drive.setDirection(DcMotor.Direction.REVERSE);
                right1Drive.setDirection(DcMotor.Direction.FORWARD);
                killRight2=true;
            }

            left1Drive.setDirection(DcMotor.Direction.REVERSE);
            left2Drive.setDirection(DcMotor.Direction.FORWARD);
            right1Drive.setDirection(DcMotor.Direction.REVERSE);
            right2Drive.setDirection(DcMotor.Direction.FORWARD);
        }
        if(strafe>0){
            if(drive>0){
                killLeft2=true;
                killRight1=true;
                left2Drive.setDirection(DcMotor.Direction.REVERSE);
                right1Drive.setDirection(DcMotor.Direction.FORWARD);
            }
            if(drive<0){
                killLeft1=true;
                killRight2=true;
                left2Drive.setDirection(DcMotor.Direction.REVERSE);
                right1Drive.setDirection(DcMotor.Direction.FORWARD);
            }
            left1Drive.setDirection(DcMotor.Direction.FORWARD);
            left2Drive.setDirection(DcMotor.Direction.REVERSE);
            right1Drive.setDirection(DcMotor.Direction.FORWARD);
            right2Drive.setDirection(DcMotor.Direction.REVERSE);
        }



        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors","LeftUpper", left1Power, "Right Upper",right1Power,"Left Lower",left2Power,"Right Lower",right2Power);
        telemetry.addData("Strafe",strafe);
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
