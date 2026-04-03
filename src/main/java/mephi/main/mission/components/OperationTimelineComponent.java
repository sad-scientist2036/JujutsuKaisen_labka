package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;

public class OperationTimelineComponent implements MissionComponent {
    private final String timestamp;
    private final String type;
    private final String description;

    private OperationTimelineComponent(Builder builder) {
        this.timestamp = builder.timestamp;
        this.type = builder.type;
        this.description = builder.description;
    }

    @Override
    public String getComponentType() {
        return "timeline";
    }

    @Override
    public String getInfo() {
        return timestamp + " [" + type + "]: " + description;
    }

    public String getTimestamp() { return timestamp; }
    public String getType() { return type; }
    public String getDescription() { return description; }

    public static class Builder {
        private String timestamp;
        private String type;
        private String description;

        public Builder timestamp(String value) { this.timestamp = value; return this; }
        public Builder type(String value) { this.type = value; return this; }
        public Builder description(String value) { this.description = value; return this; }

        public OperationTimelineComponent build() {
            if (timestamp == null || timestamp.isEmpty()) {
                throw new IllegalStateException("timestamp is required");
            }
            if (type == null || type.isEmpty()) {
                throw new IllegalStateException("type is required");
            }
            if (description == null || description.isEmpty()) {
                throw new IllegalStateException("description is required");
            }
            return new OperationTimelineComponent(this);
        }
    }
}