package scenario.base;

public class RequiredMembersResult {
    private boolean successfulResult = false;
    private String failMessage;

    public RequiredMembersResult() {
        successfulResult = true;
    }

    public RequiredMembersResult(boolean wasSuccessful, String errorMessage) {
        successfulResult = wasSuccessful;
        failMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return successfulResult;
    }

    public void succeeds() {
        successfulResult = true;
    }

    public void fails() {
        successfulResult = false;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String message) {
        failMessage = message;
    }
}
