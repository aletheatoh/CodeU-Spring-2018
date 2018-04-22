package codeu.stylization.emojis;

public class EmojiUtils {

    /** The message that has been parsed for emojis */
    private String messageParsed;

    /** The original message that was passed (unparsed for emojis) */
    private String originalMessage;

    /**
     * Initiates emoji utility functions for the String passed in the constructor.
     * @param message The message to parse emojis for
     */
    public EmojiUtils(String message) {
        originalMessage = message;
    }

    public String getMessageParsed() {
        parseEmojis();
        return messageParsed;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    private void parseEmojis() {
        String messageUnparsed = originalMessage;
        //Parse any emoji text before sending message
        messageUnparsed = messageUnparsed.replaceAll(":grinning:", "\uD83D\uDE00");
        messageUnparsed = messageUnparsed.replaceAll(":laughing:", "\uD83D\uDE02");
        messageUnparsed = messageUnparsed.replaceAll(":winking:", "\uD83D\uDE09");
        messageUnparsed = messageUnparsed.replaceAll(":sunglasses:", "\uD83D\uDE0E");
        messageUnparsed = messageUnparsed.replaceAll(":heart_eyes:", "\uD83D\uDE0D");
        messageUnparsed = messageUnparsed.replaceAll(":neutral:", "\uD83D\uDE10");
        messageUnparsed = messageUnparsed.replaceAll(":confused:", "\uD83D\uDE15");
        messageUnparsed = messageUnparsed.replaceAll(":disappointed:", "\uD83D\uDE1E");
        messageUnparsed = messageUnparsed.replaceAll(":crying:", "\uD83D\uDE2D");
        messageUnparsed = messageUnparsed.replaceAll(":angry:", "\uD83D\uDE20");
        messageParsed = messageUnparsed;
    }
}
