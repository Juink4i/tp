package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates the email address of an existing person in the address book.
 * <p>
 * The command locates a person by their {@code Name} and replaces their existing
 * {@code Email} with a new one provided by the user.
 * <p>
 * Example usage:
 * <pre>
 *     editemail p/Sergio Ramos e/iamramos@gmail.com
 * </pre>
 */
public class UpdateEmailCommand extends Command {
    public static final String COMMAND_WORD = "editemail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes this person's email.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER " + PREFIX_EMAIL + "NEW EMAIL "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "Sergio Ramos " + PREFIX_EMAIL + "iamramos@gmail.com";

    public static final String MESSAGE_SUCCESS = "Updated %1$s email to %2$s.";
    public static final String MESSAGE_INVALID_EMAIL = "%s is an invalid email";

    private final Name targetName;
    private final Email newEmail;

    /**
     * Creates a new {@code UpdateEmailCommand} to update the email of the specified person.
     *
     * @param name  the {@code Name} of the person whose email will be updated.
     * @param email the new {@code Email} to set for that person.
     */
    public UpdateEmailCommand(Name name, Email email) {
        this.targetName = name;
        this.newEmail = email;
    }

    /**
     * Executes the command to update a person's email in the address book.
     * <p>
     * This method performs the following steps:
     * <ol>
     *     <li>Checks that the model is not null.</li>
     *     <li>Finds the target {@code Person} by their {@code Name}.</li>
     *     <li>Validates that the provided email is in a valid format.</li>
     *     <li>Creates a new {@code Person} object with the updated email.</li>
     *     <li>Replaces the old person entry with the updated one in the model.</li>
     * </ol>
     *
     * @param model the {@code Model} which the command should operate on.
     * @return a {@code CommandResult} indicating success, with a message confirming the update.
     * @throws CommandException if:
     *     <ul>
     *         <li>the specified person cannot be found,</li>
     *         <li>or the new email is invalid.</li>
     *     </ul>
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson;
        String newEmailString = newEmail.toString();

        try {
            targetPerson = model.getPersonByName(targetName);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetName));
        }

        Email updatedEmail;
        try {
            updatedEmail = new Email(newEmailString);
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_EMAIL, newEmailString));
        }
        Person updatedPerson = new Person(
                targetPerson.getName(),
                targetPerson.getPhone(),
                updatedEmail,
                targetPerson.getAddress(),
                targetPerson.getTeam(),
                targetPerson.getTags(),
                targetPerson.getPosition(),
                targetPerson.getInjury()
        );

        model.setPerson(targetPerson, updatedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetName, newEmailString));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UpdateEmailCommand)) {
            return false;
        }
        UpdateEmailCommand o = (UpdateEmailCommand) other;
        return targetName.equals(o.targetName) && newEmail.equals(o.newEmail);
    }
}
