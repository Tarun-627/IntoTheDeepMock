package org.firstinspires.ftc.teamcode.Subsystems.Intake;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmClaw {
    public enum State {GRAB, DEPOSIT};
    private final Servo armclaw;

    State state = State.DEPOSIT;
    public ArmClaw(HardwareMap hardwareMap){
        armclaw = hardwareMap.get(Servo.class, "Arm Claw");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case GRAB:
                armclaw.setPosition(0.7); // Assumed grip position for claw servo
                break;
            case DEPOSIT:
                armclaw.setPosition(0.2); // Assumed deposit position for claw servo
                break;

        }
    }

    public Command armclawgrab(){
        return instant(() -> setState(State.GRAB)).requiring(this);
    }
    public Command armclawrelease(){
        return instant(() -> setState(State.DEPOSIT)).requiring(this);
    }
}
