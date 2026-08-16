package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;


import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.Extendo_Slides;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.ArmClaw;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.ArmClawPivot;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.Lift_Slides;


@TeleOp(name = "Into The Deep Mock TeleOp")

public class Teleop extends OpMode {

    // DriveTrain Define
    DcMotor leftFront, rightFront, leftBack, rightBack;
    public double SpeedMultiplier = 0.7;

    // ArmClaw Define
    private ArmClaw armclaw;

    // ArmClawPivot Define
    private ArmClawPivot armclawpivot;

    // Intake Define
    private Intake intake;

    // IntakePivot Define
    private IntakePivot intakepivot;

    // Extendo Slides Define
    private Extendo_Slides slides;

    // Lift Slides Define
    private Lift_Slides liftslides;

    @Override
    public void init() {

        // DriveTrain Init
        leftFront = hardwareMap.get(DcMotor.class, "fl");
        rightFront = hardwareMap.get(DcMotor.class, "fr");
        leftBack = hardwareMap.get(DcMotor.class, "bl");
        rightBack = hardwareMap.get(DcMotor.class, "br");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        // ArmClaw Init
        armclaw = new ArmClaw(hardwareMap);

        // ArmClawPivot Init
        armclawpivot = new ArmClawPivot(hardwareMap);

        // Intake Init
        intake = new Intake(hardwareMap);

        // Intake Pivot Init
        intakepivot = new IntakePivot(hardwareMap);

        // Extendo Slides Init
        slides = new Extendo_Slides(hardwareMap);

        // Lift Slides Init
        liftslides = new Lift_Slides(hardwareMap);
    }

    @Override
    public void loop() {

        // Mecanum DriveTrain
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double leftFrontPower = drive + strafe + turn;
        double rightFrontPower = drive - strafe - turn;
        double leftBackPower = drive - strafe + turn;
        double rightBackPower = drive + strafe - turn;

        double max = Math.max(
                Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower))
        );

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        leftFront.setPower(leftFrontPower * SpeedMultiplier);
        rightFront.setPower(rightFrontPower * SpeedMultiplier);
        leftBack.setPower(leftBackPower * SpeedMultiplier);
        rightBack.setPower(rightBackPower * SpeedMultiplier);

        // Score the artifact for the TopBox
        Command ScoreTop = sequential(
                parallel (
                        intake.intake(),
                        slides.extend()
                ),
                waitMs(5500),
                slides.retract(),
                intakepivot.intakepivoted(),
                armclaw.armclawgrab(),
                liftslides.highbox(),
                waitUntil(() -> gamepad1.rightTriggerWasPressed()),
                armclawpivot.armclawpivoted(),
                armclaw.armclawrelease()
        );

        // Score the artifact for the bottom box
        Command ScoreBottom = sequential(
                parallel (
                        intake.intake(),
                        slides.extend()
                ),
                waitMs(5500),
                slides.retract(),
                intakepivot.intakepivoted(),
                armclaw.armclawgrab(),
                liftslides.lowbox(),
                waitUntil(() -> gamepad1.rightTriggerWasPressed()),
                armclawpivot.armclawpivoted(),
                armclaw.armclawrelease()
        );

        // Score a specimen artifact
        Command ScoreSpecimenHigh = sequential(
                parallel (
                        intake.intake(),
                        slides.extend()
                ),
                waitMs(5500),
                slides.retract(),
                intakepivot.intakepivoted(),
                armclaw.armclawgrab(),
                liftslides.specimen(),
                // wait until the slides are full extended and press right trigger for pivot and release
                waitUntil(() -> gamepad1.rightTriggerWasPressed()),
                armclawpivot.armclawpivoted(),
                armclaw.armclawrelease()
        );

        Command ScoreSpecimenLow = sequential(
                parallel (
                        intake.intake(),
                        slides.extend()
                ),
                waitMs(5500),
                slides.retract(),
                intakepivot.intakepivoted(),
                armclaw.armclawgrab(),
                liftslides.specimenlow(),
                // wait until the slides are full extended and press right trigger for pivot and release
                waitUntil(() -> gamepad1.rightTriggerWasPressed()),
                armclawpivot.armclawpivoted(),
                armclaw.armclawrelease()
        );


        Command ScoreInitialize = sequential( // Get ready to execute any score command
                intake.idle(),
                intakepivot.intakeinitial(),
                armclaw.armclawrelease(),
                armclawpivot.armclawnormal(),
                liftslides.base()

        );

        Command Climb = sequential( // Get ready to execute any score command
                liftslides.highbox(), // high box is assumed to be the same height as the high rung
                waitMs(3000), // wait for driver to clip robot to rung
                liftslides.base() // retract slides

        );



        if (gamepad1.aWasPressed()) {
            Scheduler.schedule(ScoreBottom);
            Scheduler.execute();
        }

        if (gamepad1.yWasPressed()) {
            Scheduler.schedule(ScoreTop);
            Scheduler.execute();
        }

        if (gamepad1.dpadUpWasPressed()) {
            Scheduler.schedule(ScoreSpecimenHigh);
            Scheduler.execute();
        }

        if (gamepad1.dpadDownWasPressed()) {
            Scheduler.schedule(ScoreSpecimenLow);
            Scheduler.execute();
        }

        if (gamepad1.bWasPressed()) {
            Scheduler.schedule(ScoreInitialize);
            Scheduler.execute();
        }

        if (gamepad1.leftTriggerWasPressed()) {
            Scheduler.schedule(Climb);
            Scheduler.execute();
        }


    }
}