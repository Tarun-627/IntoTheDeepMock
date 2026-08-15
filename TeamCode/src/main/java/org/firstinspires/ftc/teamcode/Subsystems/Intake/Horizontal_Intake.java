package org.firstinspires.ftc.teamcode.Subsystems.Intake;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Horizontal_Intake {

    public enum State {INTAKE, OUTTAKE, IDLE};

    private final DcMotorEx intake;
    private State state = State.IDLE;
    public Horizontal_Intake(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotorEx.class, "horizontal intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case INTAKE:
                intake.setPower(1.0);
                break;
            case OUTTAKE:
                intake.setPower(-1.0);
                break;
            case IDLE:
                intake.setPower(0);
                break;
        }
    }

    public Command in(){
        return instant(() -> setState(State.INTAKE)).requiring(this);
    }
    public Command out(){
        return instant(() -> setState(State.OUTTAKE)).requiring(this);
    }
    public Command idle(){
        return instant(() -> setState(State.IDLE)).requiring(this);
    }
}
