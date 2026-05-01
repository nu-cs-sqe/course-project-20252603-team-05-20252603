# Week 4 Project Feedback by PM/TA

**Dedicated PM/TA**: Peer Mentor (Local Repository Review)

## How to Read This Feedback
> [!NOTE]
> **Purpose.** This feedback focuses on your team's progress and collaboration. It is meant as guidance, not judgement.

> [!IMPORTANT]
> **Scope.** For the BVA and TDD items, the PM/TA evaluates only the `main` branch. Ongoing work in feature branches will be evaluated after it is merged. If you'd like early feedback on work in progress, please reach out to your PM/TA directly.

> [!TIP]
> **Mistakes are expected :).** As the instructor mentioned in class, early mistakes are part of the learning process. As long as your team addresses the issues after you get the feedback, your grade will not suffer from them.

## Checklist
Status:
- ✅: All done/Good job!
- ⚠️: Attention needed
- ❌: Significant issue found
- ➖: No basis to evaluate

### Software Process Quality
| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ✅        | Verified by team confirmation: protections are configured to require PR approval before merge/push to main. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ✅        | CI workflow is present in `.github/workflows/main.yml` and build status badge is included in README; marked complete per current repository setup and team confirmation. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ❌        | Based on team confirmation, functionality work items are missing on the board, so steady/detailed board usage is not met. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ❌        | Missing. Team confirmed there are no relevant functionality items on the board to include user stories/use cases. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ⚠️        | A design folder exists, but `docs/design/README.md` is only instruction text. No concrete class design diagram or architecture description found in repository. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ❌        | Missing. Team confirmed task assignments are not documented on the board. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 4   | Each active feature branch has an open draft PR against main.                                                                                                | ✅        | Completed. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ❌        | `docs/bva/README.md` is a template/instruction file only. No per-class BVA analysis files and no documented test cases for implemented behavior. | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ❌        | No non-UI implementation and no non-UI tests are present (`src/main/java/domain/.gitkeep`, `src/test/java/domain/.gitkeep`). Commit history primarily shows setup/docs/CI updates, not red-green-refactor cycles. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 7 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️        | Weekly report file exists, but content is still mostly placeholder/template text (e.g., "Week X" template and generic task entries). Professional completeness is not yet met. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| #  | Item                                                                                                                                                             |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 8  | README is updated properly, including the project name, contributors, and build status badge. In addition, the team should specify the GUI library if appliable. | ✅        | Completed per evaluator decision. README criteria are considered satisfied for this checkpoint. | Canvas assignment Project: Setup                                                  |
| 9  | Overall progress on "Game Setup Phase"                                                                                                                           | ❌        | Current implementation progress is very limited: UI entry file is empty/whitespace, domain and test directories contain only `.gitkeep`, and requirements document is still a placeholder. | Canvas assignment Project: Week 4 Guidance                                        |
| 10 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                          | ⚠️        | Contribution/discussion appears concentrated among only two team members. Collaboration is present but not yet broadly distributed across the full team. |                                                                                   |

## Additional Comments
Right now the project is still at a very early setup stage, which is okay for this point in the quarter. The priority should be to move from placeholders to actual content: add real functionality tasks to the board, document design clearly, and start implementing small features with corresponding tests. Once you begin committing actual code with tests and BVA, progress will become much more visible. Focus on getting that first end to end slice working.