# Contributing to croc-app

If you want to help:

1. **Open an issue/discussion for the PR discussion first**
2. Keep pull requests focused and easy to review.
3. Include screenshots or recordings for UI changes when possible.
4. Mention migration impact, behavior changes, and testing notes in the PR description.

## Vendoring Go Dependencies

croc-app builds croc from vendored source in `third_party/croc-src`.

Whenever dependencies are updated or re-vendored, you must commit the `vendor/` output:

```bash
cd third_party/croc-src
go mod tidy
go mod vendor
go build ./...
```

`vendor/` also carries app patches to `tailscale.com` (Android netmon/netcheck fallbacks and `magicsock.DERPStartedChan`, used by `internal/tailcat`). `go mod vendor` discards them, so re-apply them afterwards (see commit `3227195`) or the build fails.

## Areas where help is especially useful:

- transfer edge cases
- accessibility improvements
- UI refinement
- test coverage
- new quality of life changes
