# Week 5 Project Feedback by PM/TA

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

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedback. | ❌        | Feedback PR #4 remains open/unmerged. Please review, discuss findings, and merge to show you've processed the feedback. |                                  |

### Software Process Quality
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1 | Each active feature branch has an open draft PR against main.                                                                                                | ✅        | Two active feature branches, both with open PRs. Keep the momentum going! |
| 2 | The team has a "definition of done" (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ❌        | Still waiting for concrete class BVA analysis files. No per-method test coverage yet. Start with one simple domain class! |
| 3 | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ❌        | No domain implementation yet. Focus week 5 on adding at least one domain class with tests using red-green-refactor commits. |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week's planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ❌        | Report template is in place but still shows Week 3 placeholder content. Add Week 4/5 real entries with PR links this week! |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) | ❌        | Still in pre-implementation. Get the first domain class + tests working to unblock game setup logic. You've got this! |
| 6 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                                                | ⚠️        | Only 2 active contributors visible. Encourage the other 2 teammates to jump in on PRs, design, and board discussion. |

### The following items are not checked by the reviewer as they were checked in the previous weeks
But if your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tagging them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) |           |                     | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  |           |                     | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | See below | See breakdown below | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   |           |                     | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   |           |                     | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             |           |                     | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |

## Additional Comments
You are still very early in the implementation stage, so the main focus for next week should be moving from setup and planning into actual working code. Start small with one domain class and build it out with tests so you can establish the TDD workflow early. Once that is in place, everything else like BVA and game flow will become much easier to build on. Keep coordinating as a team and make sure all members are actively contributing through PRs and discussions.