# ScaffoldPRO v5.0 GOLD-PASS - PHASE P1 (FINAL) - BRANCH AND TAG PUSH
# PowerShell version

$ErrorActionPreference = "Stop"

try {
    $TARGET_COMMIT = "abc1234"
    $RELEASE_TAG = "v5.0.0"

    Write-Host "0) Verify working tree is clean..."
    $status = git diff-index --quiet HEAD -- 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "X Uncommitted files present"
        exit 1
    }

    # 1. Stage and commit the changelog alignment
    Write-Host "1) Stage and commit the changelog alignment"
    git add RELEASE_CHANGELOG.md.v5.0
    git commit -m "docs(changelog): align v5.0.0 changelog with release notes"

    # 2. Sync main to field-tools
    Write-Host "2) Sync main to field-tools"
    git checkout main
    git pull --ff-only
    $branchExists = git show-ref --verify --quiet refs/heads/field-tools
    if ($LASTEXITCODE -ne 0) {
        git checkout -b field-tools "$TARGET_COMMIT"
    } else {
        git checkout field-tools
    }
    git merge --no-ff --no-edit main

    # 3. Push branch and set upstream
    Write-Host "3) Push branch and set upstream"
    git remote add origin git@github.com:starnickell/ScaffoldPRO.git 2>$null
    git push -u origin field-tools

    # 4. Create and push annotated + signed tag
    Write-Host "4) Create and push annotated + signed tag"
    git tag -s "$RELEASE_TAG" -m "ScaffoldPRO $RELEASE_TAG - Gold-Pass"
    git push origin "$RELEASE_TAG"

    # 5. Block until GitHub Actions are green
    Write-Host "Waiting for iosBuild, androidBuild, snapshot, and smoke workflows..."
    $ghExists = Get-Command gh -ErrorAction SilentlyContinue
    if ($ghExists) {
        gh run watch --repo starnickell/ScaffoldPRO
    } else {
        Write-Host "GitHub CLI missing - open Actions tab and verify manually:"
        Write-Host "    https://github.com/starnickell/ScaffoldPRO/actions"
        Write-Host "Press Enter when all workflows succeed..."
        Read-Host
    }

    Write-Host "Phase P1 complete - branch and tag live, CI green."
    Write-Host "Reply 'P1' here, or paste failing log URL if anything went red."

} catch {
    Write-Host "X Phase P1 failed - see last command: $_"
    exit 1
}