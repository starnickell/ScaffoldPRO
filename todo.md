## Final RC-Polish Delta (v5.0-rc1-refresh-2)

### 1. E2E Smoke Automation
- [ ] **Snapshot Baseline Push:**
    - [ ] Ensure `ios-build.yml` includes script/steps to push/update snapshot baselines.
    - [ ] Ensure `android-build.yml` includes script/steps to push/update snapshot baselines.
- [ ] **Create `.github/workflows/smoke.yml`:**
    - [ ] Workflow `needs: [ios-build, android-build]`.
    - [ ] **iOS Smoke Test Job:**
        - [ ] `runs-on: macos-13` (image `xcode15.3`).
        - [ ] Runs `detox test -c ios.sim.release`.
    - [ ] **Android Smoke Test Job:**
        - [ ] `runs-on: ubuntu-latest`.
        - [ ] Runs `maestro cloud --apiKey ${{ secrets.MAESTRO_KEY }} flows/login.yaml`.
- [ ] **CI Integration:**
    - [ ] Add `smoke` job to protected branch required status checks.
    - [ ] Ensure CI fails if either smoke test job (Detox or Maestro) exits non-zero.

### 2. Secrets & Runner Matrix
- [ ] **Repository Secrets:**
    - [ ] Create repository secret: `MAESTRO_KEY`.
    - [ ] Create repository secret: `DETOX_IOS_SCHEME`.
- [ ] **Runner Pinning:**
    - [ ] Pin all macOS runners in all workflows to `macos-13` with `macos-image: xcode15.3`.

### 3. Artifact Hygiene
- [ ] For every `actions/upload-artifact@v4` step in all workflows:
    - [ ] Add `retention-days: 14`.
    - [ ] Add `if-no-files-found: error`.

### 4. Documentation & Visibility
- [ ] **README.md:**
    - [ ] Add a CI badge for the `smoke` workflow at the top of `README.md`.
    - [ ] Append a short "Running smoke tests locally" section (include Detox and Maestro commands).
- [ ] **Update `/docs/build-pipeline.md`:**
    - [ ] Reflect the new `smoke` job and any new secrets.
- [ ] **Update `project_plan.md`:**
    - [ ] Reflect the new `smoke` job and any new secrets.
- [ ] **Update `/docs/e2e.md`:**
    - [ ] Append the exact YAML diff of `smoke.yml` for traceability.

### 5. Structured Action Log
- [ ] Create `/docs/rc1_refresh2_changelog.json`.
- [ ] For every file touched in this delta, write a JSON entry to `rc1_refresh2_changelog.json` with:
    ```json
    {
      "timestamp": "ISO-8601",
      "file": "path/to/file",
      "action": "added | modified",
      "commit": "short-sha",
      "comment": "one-line purpose"
    }
    ```
- [ ] Ensure `rc1_refresh2_changelog.json` is committed.

### 6. Execution Flow
- [ ] Create/checkout `rc-polish` branch.
- [ ] Implement all above items.
- [ ] Commit changes.
- [ ] Push `rc-polish` branch.
- [ ] Open Pull Request.
- [ ] Wait for green CI (including `smoke` job).
- [ ] Merge Pull Request.
- [ ] Tag `v5.0-rc1-refresh-2`.
- [ ] Notify Linear (simulated: provide comment text).

### 7. Release Artifacts
- [ ] Attach generated artifacts (`ipa`, `aab`, contrast reports, detox/maestro logs) to the GitHub release for `v5.0-rc1-refresh-2`.

### 8. Final Reporting to User
- [ ] Reply with the PR link.
- [ ] Reply with the commit SHA of `smoke.yml`.
- [ ] Reply with a summary confirming all eight punch-list items from the latest `pasted_content.txt` are now *real*, not simulated.

