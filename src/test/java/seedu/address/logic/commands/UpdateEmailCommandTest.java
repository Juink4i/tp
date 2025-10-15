package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link UpdateEmailCommand}.
 */
public class UpdateEmailCommandTest {

    @Test
    public void execute_personExists_updatesEmail() throws Exception {
        Person person = new PersonBuilder().build();
        Name name = person.getName();
        Email newEmail = new Email("newemail@example.com");

        final Person[] setTarget = new Person[1];
        final Person[] setEdited = new Person[1];

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }

            @Override
            public void setPerson(Person target, Person editedPerson) {
                setTarget[0] = target;
                setEdited[0] = editedPerson;
            }
        };

        UpdateEmailCommand command = new UpdateEmailCommand(name, newEmail);
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(UpdateEmailCommand.MESSAGE_SUCCESS, name, newEmail.toString()),
                result.getFeedbackToUser());
        // verify setPerson called with correct values
        assertEquals(person, setTarget[0]);
        assertEquals(newEmail, setEdited[0].getEmail());
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() {
        Name name = new Name("Non Existent");
        Email email = new Email("valid@example.com");
        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                throw new seedu.address.model.person.exceptions.PersonNotFoundException();
            }
        };

        UpdateEmailCommand command = new UpdateEmailCommand(name, email);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            assertEquals(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, name), e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for missing person.");
        }
        throw new AssertionError("Expected CommandException for missing person.");
    }

    @Test
    public void equals() {
        Name sergio = new Name("Sergio Ramos");
        Name leo = new Name("Lionel Messi");
        Email e1 = new Email("a@example.com");
        Email e2 = new Email("b@example.com");
        UpdateEmailCommand c1 = new UpdateEmailCommand(sergio, e1);
        UpdateEmailCommand c1Copy = new UpdateEmailCommand(sergio, e1);
        UpdateEmailCommand c2 = new UpdateEmailCommand(leo, e1);
        UpdateEmailCommand c3 = new UpdateEmailCommand(sergio, e2);

        assertTrue(c1.equals(c1));
        assertTrue(c1.equals(c1Copy));
        assertFalse(c1.equals(null));
        assertFalse(c1.equals(1));
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
    }
}
