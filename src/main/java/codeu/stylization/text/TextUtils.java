package codeu.stylization.text;

public class TextUtils {

    /** The message that has been parsed for text stylization */
    private String messageParsed;

    /** The original message that was passed (unparsed for text stylization) */
    private String originalMessage;

    /**
     * Initiates text utility functions for the String passed in the constructor.
     * @param message The message to parse text stylizations for
     */
    public TextUtils(String message) {
        originalMessage = message;
    }

    public String getMessageParsed() {
        parsedTextStyles();
        return messageParsed;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    //Bold - **, Underline - //, Italicize - ~, Strikethrough - =
    private void parsedTextStyles() {
        String messageUnparsed = originalMessage;
        messageUnparsed = replaceAllStyles("underline", messageUnparsed);
        messageUnparsed = replaceAllStyles("bold", messageUnparsed);
        messageUnparsed = replaceAllStyles("italicize", messageUnparsed);
        messageUnparsed = replaceAllStyles("strikethrough", messageUnparsed);
        messageParsed = messageUnparsed;
    }

    /**
     * Function will return -1, -1 if none of the stylizations encountered is correctly formatted.
     * Otherwise, it will return the indices in which it encounters a correctly formatted stylization.
     * @param stylization The type of stylization the method should look for
     * @param messageUnparsed The message that is currently unparsed for that particular stylization
     * @return An integer array that returns the indicies in which the stylization first appears (includes
     * the style indicators (*, /, ~, =)
     */
    private int[] getIndexOfStyle(String stylization, String messageUnparsed) {
        int[] indicies = new int[2];
        indicies[0] = -1; indicies[1] = -1;
        String style = "*";
        if(stylization.equals("underline")) {
            style = "/";
        }
        if (stylization.equals("italicize")) {
            style = "~";
        }
        if (stylization.equals("strikethrough")) {
            style = "=";
        }
        if (messageUnparsed.indexOf(style) == -1) return indicies;
        else {
            if (messageUnparsed.substring(messageUnparsed.indexOf(style) + 1).indexOf(style) == -1) return indicies;
            else {
                indicies[0] = messageUnparsed.indexOf(style);
                indicies[1] = messageUnparsed.substring(messageUnparsed.indexOf(style) + 1).indexOf(style) + 1 + indicies[0];
                return indicies;
            }
        }
    }

    /**
     * Function will stylize the text with HTML tags.
     * @param stylization The type of stylization the method should look for
     * @param messageUnparsed The message that is currently unparsed for that particular stylization
     * @return The message value as a String that is correctly parsed through
     */
    private String replaceAllStyles(String stylization, String messageUnparsed) {
        String style = "<strong>";
        String styleEnding = "</strong>";
        if(stylization.equals("underline")) {
            style = "<u>";
            styleEnding = "</u>";
        }
        if (stylization.equals("italicize")) {
            style = "<i>";
            styleEnding = "</i>";
        }
        if (stylization.equals("strikethrough")) {
            style = "<strike>";
            styleEnding = "</strike>";
        }
        StringBuilder messageReplaced = new StringBuilder();
        int[] indicies = getIndexOfStyle(stylization, messageUnparsed);
        if(indicies[0] == -1) return messageUnparsed;
        while (messageUnparsed.length() != 0 && indicies[0] != -1) {
            if (indicies[0] == 0) {
                messageReplaced.append(style + messageUnparsed.substring(1, indicies[1]) + styleEnding);
            }
            else {
                messageReplaced.append(messageUnparsed.substring(0, indicies[0]) + style + messageUnparsed.substring(indicies[0]
                        + 1, indicies[1]) + styleEnding);
            }
            if(indicies[1] == messageUnparsed.length() - 1) {
                messageUnparsed = "";
            } else {
                messageUnparsed = messageUnparsed.substring(indicies[1] + 1);
            }
            indicies = getIndexOfStyle(stylization, messageUnparsed);
        }
        if(messageUnparsed.length() != 0) {
            messageReplaced.append(messageUnparsed);
        }
        return messageReplaced.toString();
    }
}
