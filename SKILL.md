---
name: focused-code-change
description: 'Use when implementing or debugging a focused code change: locate the controlling behavior, form a falsifiable hypothesis, make the smallest safe edit, and validate it with a narrow executable check.'
argument-hint: '[task or failing behavior]'
user-invocable: true
---

# Focused Code Change

Use this skill for a small, concrete implementation or debugging task where the goal is to change behavior safely without broad repository exploration.

## Procedure

1. **Identify the anchor.** Start from the named file, symbol, failing behavior, command, test, or nearby implementation surface. Search only enough to find the code that directly computes, mutates, or controls the behavior.
2. **State a local hypothesis.** Before editing, write down one falsifiable explanation for the current behavior and the nearby code path it depends on. Name one cheap check that could disconfirm it.
3. **Choose the smallest testable edit.** Prefer the existing abstraction, helper, and style. Preserve public APIs and unrelated user changes. If confidence is incomplete, make a small reversible probe that exposes the relevant type, control-flow, or behavior mismatch.
4. **Validate immediately.** After the first substantive edit, run the narrowest available executable check in this order:
   - the failing or behavior-scoped check;
   - a focused test for the touched slice;
   - a narrow compile, lint, or typecheck command.
5. **React to the result.** If validation supports the hypothesis but exposes a local defect, repair the same slice and rerun the same check. If it falsifies the hypothesis, take one nearby hop to the code that more directly controls the behavior. Do not reopen broad exploration unless the nearby paths are exhausted.
6. **Finish the change.** Run at least one post-edit executable validation. Review the final diff for scope, unintended edits, and missing tests. Report what changed and exactly what was validated; mention any unavailable or unrelated checks.

## Decision Rules

- If a nearby test or call site resolves ownership, use it before reading broader documentation.
- If multiple paths seem plausible, choose the one with the strongest falsifiable hypothesis and the cheapest discriminating check.
- If no focused executable check exists, use the smallest available compile, lint, or typecheck; use a diff review only when no command is available.
- Do not fix unrelated failures or revert changes made by the user.
- Do not add abstractions unless they remove real complexity or match an established local pattern.

## Completion Criteria

- The controlling code path is identified.
- The edit is minimal and consistent with local conventions.
- A focused executable validation passes, or its failure and scope are clearly reported.
- The final change does not include unrelated files or refactors.
