package mch.project.hello_world.purposeservice.domain.purpose;

public enum PurposeType {
    SOLO("혼자"), TEAM("같이"), ETC("기타");

    private final String description;

    PurposeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
