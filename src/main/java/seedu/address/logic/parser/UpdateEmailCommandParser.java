package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.commands.UpdateEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@link UpdateEmailCommand} object.
 * <p>
 * This parser expects user input of the form:
 * <pre>
 *     editemail p/PLAYER_NAME e/NEW_EMAIL
 * </pre>
 * Example:
 * <pre>
 *     editemail p/Sergio Ramos e/iamramos@gmail.com
 * </pre>
 * It extracts both the player's name and new email address from the input,
 * validates their presence and format, and then constructs an {@code UpdateEmailCommand}.
 */
public class UpdateEmailCommandParser implements Parser<UpdateEmailCommand> {

    /**
     * Parses the given {@code userInput} string and returns an {@code UpdateEmailCommand} object
     * for execution.
     *
     * @param userInput the raw command input from the user (e.g. "p/Sergio Ramos e/iamramos@gmail.com").
     * @return a new {@code UpdateEmailCommand} object containing the parsed name and email.
     * @throws ParseException if the input format is invalid, missing required prefixes,
     *                        or cannot be parsed into valid {@code Name} or {@code Email} objects.
     */
    @Override
    public UpdateEmailCommand parse(String userInput) throws ParseException {
        // Tokenize using BOTH prefixes
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_PLAYER, PREFIX_EMAIL);

        // Command should not have anything before the first prefix
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
        }

        // Make sure there’s no duplicate player prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_EMAIL);

        // Check both are present
        if (argMultimap.getValue(PREFIX_PLAYER).isEmpty() || argMultimap.getValue(PREFIX_EMAIL).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEmailCommand.MESSAGE_USAGE));
        }

        // Parse the name and email
        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        Email newEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        // Return a new command object
        return new UpdateEmailCommand(playerName, newEmail);
    }
}
