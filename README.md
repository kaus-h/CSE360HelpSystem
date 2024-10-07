Product Vision

ASU Students come into CSE 360 through many paths.  Some have significant programming experience, while others have just the required course to be admitted to the class.  The goal of the class is to provide every member of the class with a solid foundational survey of Software Engineering principles and concepts, practical individual experience applying some of them, and a team experience in producing a realistic and complex application demonstrating a deeper understanding of a set of these principles and concepts and the ability to work effectively as a member of a team.

One challenge that many face is finding an effective and reliable source of information to help them quickly and effectively accomplish the tasks required so they have time to experiment and dig deeper if they are so inclined or focus time on other courses or activities if they are more important.

We envision a help system that makes it easier for ASU to provide current, accurate, and relevant information to CSE 360 students.  It must be done in a way that does not waste the student's time by providing information that is too complex or too simplified for the situation the student is facing.  For the system to support the unique needs of each student well, personal information must be used.  For example, the student may indicate that they are excellent in programming in Java, are familiar but not skilled with Eclipse, and are a true beginner with JavaFX and GitHub.  Some students may feel uncomfortable if anyone else sees this personal information.  (To be honest, such personal information should never be made available to others without expressed permission being given.)  When the student can't quickly find the information they need, an easy-to-use method must be provided to let the instructional team know the issue and what was viewed so they can provide new information that is more likely to be effective.  Similarly, the system should allow the student to specify "more like this but with more detail" or "more like this with less detail".  One critical source of data for the help system must come from Ed Discussion, so the questions and potential answers that are important this semester can be supported by the help system.

The instructional team must be able to quickly replace materials from an old version of the class with a new version of the class.  When a new tool becomes available (e.g., the web page to register for GitHub changes), it is easy for the team to find and update (or remove) the existing help entries as well as provide new ones.  A survey of the Ed Discussions data must be analyzed to identify topics or issues that the help system should be able to address.  Are there reasons why students didn't use the help system?  Do the key terms set for the help item need to be enhanced to make it easier for the student to find the needed help?  (If the student uses words that are not in the list of key terms, mechanical search methods will not work.  Maybe newer AI search methods can be used.)  Each new help item needs tags so that searching can be effective, as well as information about the target user (e.g., beginner, expert), and so forth.

An administrator needs to be able to back up and restore the help data and the users, and add, edit, and remove individual articles and individuals.  Just because this individual can add, remove, back up, and restore users does not mean they should have access to personal/private information.  Additional actions include resetting a user's access with a one-time password so a user can get back into the system and set up a new password without the admin knowing the old, one-time, or new passwords for a user.

There must be room in your product's implementation to support other roles (beyond those stated here) and provide actions these different roles can perform.

Development Overview

During the last three development phases, the team must produce (and update if needed) the requirements, architecture, design, code, test cases, and demonstration to make it clear how the requirements flow gracefully through all the software development stages.  Unless special arrangements are made, all four phases of the application development must be written with a Graphical User Interface using JavaFX.

The First Phase

The first submission must focus on establishing the foundation for a secure and private identity mechanism for the application.  Your team must demonstrate that the requirements for users and roles stated above can be implemented.  (Be aware that an admin might also be a student.  How can that be supported?)

Each user has account information that includes:

an email address
a user name
a password (a non-string data type)
a flag indicating that the password is a one-time password that requires the generation of a new password
a date and time after which a one-time password is no longer valid
the individual's name (first, middle, last, preferred)
a list of system-recognized topics; for each one of the following: beginner, intermediate, advanced, expert (with intermediate being the default)
The requirements, architecture, and design at a high level need to be sketched out for the whole help system, even though many of the details are not yet known in detail.  The Professor will hold evening events (that will be recorded and made available to all) where students can elicit information for the application.

Two screencasts must be provided.  The first is a technical screencast that explains the flow from the requirements through to the working application.  The second is a how-to-use screencast aimed at three separate groups: students, admins, and instructional team members.

The Second Phase

The project's second phase will focus on the data at the heart of the help system.  The user roles of focus for this phase are admins and instructors.

Each help item consists, at a minimum, of:

a unique header including information such as the level of the article (e.g., beginner, intermediate, advanced, expert), grouping identifiers (so it is easy for the instructional team to update or delete a related set of articles), and other system information that might limit who can read the article for sensitive/restricted information
a title
a short description (This is like an abstract for a paper, but shorter.  It is displayed when to enable the user to select which of several help items returned to read first.  This is similar to the short text provided by a web search engine after a query.)
a set of keywords or phrases to facilitate the search process for students
the body of the help article
a set of links to reference materials and related articles
We already know that methods for backing up and restoring these articles are needed.  Methods for adding, removing, and updating them must be provided.  Methods for searching and displaying them that fit the request of the user must be provided.  In addition, data must be grouped so that data tied to specific tools can be found and updated or removed when preparing for the next semester.  This second phase needs to be integrated with an updated first phase.

Two screencasts must be provided.  The first is a technical screencast that explains the flow from the requirements through to the working application.  The second is a how-to-use screencast aimed at three separate groups: students, admins, and instructional team members.

The Third Phase

A major enhancement is the interface for student users and the methods to facilitate searching, displaying, and asking the system operations team for assistance when the student is not able to find what is needed.

The third phase also adds encrypting data that must be kept private.  When determining whether or not an article should appear in the returned list, an access protocol must be used to determine if this user belongs to a group of users who have been granted access or if this user has been given individual access.

The user interface was responsible for security and privacy in the previous two deliverables.  It was written so that a user role can only see data that is appropriate for that user.  The implementation in this phase is enhanced to ensure that even if memory is dumped and analyzed, private and sensitive data will not be visible.  From the beginning, the architecture and the design must be performed knowing that encryption will be added in this third phase. Significant rewriting of code to implement encryption in this phase must be avoided.  The third phase also requires an initial set of JUnit tests. The team must demonstrate their understanding of automated test suites and their ability to create and use them.

Two screencasts must be provided.  The first is a technical screencast that explains the flow from the requirements through to the working application.  The second is a how-to-use screencast aimed at three separate groups: students, admins, and instructional team members.

The Fourth Phase

The fourth phase refines the requirements, architecture, design, code (and internal documentation), and test suites so it is clear that they flow gracefully from one to another and all are in alignment.  Feedback from the previous phases must be addressed.  Additional functions and user interface elements must be provided as needed to convince potential users about the value and ease of use that has been designed into the system, carefully implemented, and thoroughly tested.

Two screencasts must be provided.  The first is a technical screencast that explains the flow from the requirements through to the working application.  The second is a how-to-use screencast aimed at three separate groups: students, admins, and instructional team members.

Phase One Requirements

The very first person to log into the application (e.g., the list of users is empty) is assumed to be an Admin.  The system requires the first user to specify a username and password.  An account is created for that username and password, and that user is assigned the role of Admin.  At that point, the user is directed back to the original login.
When creating a password, the password must be entered two times, and they must match.
Before a user can use the system, when logging in again they are first taken to a "Finish setting up your account" page.  The "Finish setting up your account" page requires the user to specify an email address and their name.  There are four fields associated with a name: first, middle, last, and optionally preferred first name.  (If the user specifies an optional preferred first name, this name will be used when displaying messages to that user from the application.)
The system must support multiple roles.  The following are required minimum roles: Admin, Student, Instructor.
A user may have more than one role.  If a user has more than one role, after signing in, the user must specify which role is appropriate for this session.  If the user has just one role, the user is taken to a page to the home page for that role.  For Phase 1, the Student and Instructor role home pages have only one option, and that is to log out.
An Admin can perform the following:
Invite an individual to join the application.  A one-time code is provided that allows a new user to create an account.  The standard login page allows the user to provide a username to start the login process or a different input field in which they can enter the invitation code.  The Admin must specify which role(s) this invited user is being given when producing the invitation.
Reset a user account.  A one-time password and an expiration date and time is set.  The next time the user tries to log in, they must use the one-time password, and the only action possible is to set up a new password.  Before being given access to set up a new password, the system checks to see if the date and time are proper given the deadline.  Once the new password has been set, the user is directed back to the login page. Logging in with the one-time password resets the flag so it can't be used again.
Delete a user account.  An "Are you sure?" message must be answered with "Yes" to do the delete.
List the user accounts. A list of all the user accounts with the user name, the individual's name, and a set of codes for the roles is displayed.
Add or remove a role from a user.
Log out.
All other users can perform the following:
At the login page, fill in the one-time invitation code to be allowed to establish an account.  The only action allowed when establishing an account is to specify a username and password.  An account is created for that username and password with the role(s) associated with the one-time invitation.  At that point, the user is directed back to the original login.
As described above, they must finish setting up their account.  Once the account is set up, they then have access to the home page to which they have been assigned or to the page where they can select which role is appropriate for this session and then the home page for that role.
For Phase 1, after logging in, finishing the account setup, and selecting a role for this session (if they have multiple roles) they are taken to a home page for that role where the only option is to log out.
