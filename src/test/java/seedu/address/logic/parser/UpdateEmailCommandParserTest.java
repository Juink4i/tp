package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateEmailCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;

/**
 * Unit tests for {@link UpdateEmailCommandParser}.
 */
public class UpdateEmailCommandParserTest {

    private final UpdateEmailCommandParser parser = new UpdateEmailCommandParser();

    @Test
    public void parse_validArgs_success() {
        Name expectedName = new Name("Amy Bee");
        Email expectedEmail = new Email("amy@example.com");
        UpdateEmailCommand expectedCommand = new UpdateEmailCommand(expectedName, expectedEmail);
        assertParseSuccess(parser, PLAYER_DESC_AMY + EMAIL_DESC_AMY, expectedCommand);
        // any order
        assertParseSuccess(parser, EMAIL_DESC_AMY + PLAYER_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_missingPrefixes_failure() {
        // missing both prefixes
        assertParseFailure(parser, "Amy Bee amy@example.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
        // missing player prefix
        assertParseFailure(parser, EMAIL_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
        // missing email prefix
        assertParseFailure(parser, PLAYER_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleNotEmpty_failure() {
        assertParseFailure(parser, "xyz" + PLAYER_DESC_AMY + EMAIL_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidEmail_failure() {
        assertParseFailure(parser, PLAYER_DESC_AMY + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate player prefix
        assertParseFailure(parser, PLAYER_DESC_AMY + PLAYER_DESC_AMY + EMAIL_DESC_AMY,
                MESSAGE_DUPLICATE_FIELDS + "pl/");
        // duplicate email prefix
        assertParseFailure(parser, PLAYER_DESC_AMY + EMAIL_DESC_AMY + EMAIL_DESC_AMY,
                MESSAGE_DUPLICATE_FIELDS + "e/");
    }
}
