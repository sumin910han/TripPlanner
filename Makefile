JAVAFX_PATH := /Users/hansumin/desktop/CS400/javafx-sdk-20/lib
VMARG := --module-path $(JAVAFX_PATH) --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
COMPILED_FILES := $(patsubst %.java, %.class, $(wildcard *.java))

default: run

warn:
	@echo "WARNING: IF YOU GET ERRORS, YOU NEED TO SET THE VMARG VARIABLE TO YOUR JAVAFX INSTALLATION"

%.class : %.java
	javac -cp .:junit5.jar:JavaFXTester.jar $(VMARG) $<

compile: warn $(COMPILED_FILES)
	@echo "Done!"

runFrontendDeveloperTests: compile
	java $(VMARG) -jar junit5.jar -cp .:JavaFXTester.jar --select-class=TripPlannerFrontendTests || true

runBackendDeveloperTests: compile
	# java $(VMARG) -jar junit5.jar -cp . --select-class=BackendDeveloperTests || true
	@echo "Backend developer tests disabled because missing BD"

runAlgorithmEngineerTests: compile
	java $(VMARG) -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests || true

runDataWranglerTests: compile
	java $(VMARG) -jar junit5.jar -cp . --select-class=DataWranglerTests || true

# There should not be a main class because JavaFX runs really weirdly.
# If there is a main class, it should instead run the TripPlannerFrontendFD class in a separate 
# JVM using Runtime.getRuntime().exec(...);
run: compile
	java $(VMARG) TripPlannerFrontendFD

runTests: runFrontendDeveloperTests runBackendDeveloperTests runAlgorithmEngineerTests runDataWranglerTests
	@echo "Done running tests"

clean:
		rm *.class
