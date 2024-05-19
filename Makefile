# Makefile for Java project

# Directories
SRC_DIR = src/main/java
RES_DIR = src/main/resources
BUILD_DIR = build
JAR_DIR = jar

MAIN_CLASS = controller.MainController
MAIN_CLASS_FILE = controller/MainController.java

# Targets
.PHONY: all clean run

all: $(BUILD_DIR) compile copy-resources

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

compile: $(BUILD_DIR)
	javac -d $(BUILD_DIR) --class-path $(SRC_DIR) $(SRC_DIR)/$(MAIN_CLASS_FILE)

copy-resources: compile
	cp -r $(RES_DIR)/* $(BUILD_DIR)

run: copy-resources
	cd $(BUILD_DIR) && java $(MAIN_CLASS)

jar: compile copy-resources
	jar cfe $(JAR_DIR)/shooter.jar $(MAIN_CLASS) -C $(BUILD_DIR) .

clean:
	rm -rf $(BUILD_DIR) $(JAR_DIR)
