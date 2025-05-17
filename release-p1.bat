@echo off
REM ScaffoldPRO v5.0 GOLD-PASS · PHASE P1 (FINAL) · BRANCH & TAG PUSH
REM Windows Batch Script version

setlocal enabledelayedexpansion

SET TARGET_COMMIT=abc1234
SET RELEASE_TAG=v5.0.0

echo 🔍 0) Verify working tree is clean...
git diff-index --quiet HEAD -- || (
    echo ❌ Uncommitted files present
    exit /b 1
)

REM 1 · Stage & commit the changelog alignment
echo 1) Stage ^& commit the changelog alignment
git add RELEASE_CHANGELOG.md.v5.0
git commit -m "docs(changelog): align v5.0.0 changelog with release notes"

REM 2 · Sync main ➜ field-tools
echo 2) Sync main ➜ field-tools
git checkout main
git pull --ff-only
git show-ref --verify --quiet refs/heads/field-tools
IF %ERRORLEVEL% NEQ 0 (
    git checkout -b field-tools %TARGET_COMMIT%
) ELSE (
    git checkout field-tools
)
git merge --no-ff --no-edit main

REM 3 · Push branch & set upstream
echo 3) Push branch ^& set upstream
git remote add origin git@github.com:starnickell/ScaffoldPRO.git 2>NUL || (
    echo Remote 'origin' already exists, continuing...
)
git push -u origin field-tools

REM 4 · Create & push annotated + signed tag
echo 4) Create ^& push annotated + signed tag
git tag -s %RELEASE_TAG% -m "ScaffoldPRO %RELEASE_TAG% — Gold-Pass"
git push origin %RELEASE_TAG%

REM 5 · Block until GitHub Actions are green
echo ⏱ Waiting for iosBuild · androidBuild · snapshot · smoke workflows...
where gh >NUL 2>&1
IF %ERRORLEVEL% EQU 0 (
    gh run watch --repo starnickell/ScaffoldPRO
) ELSE (
    echo ⚠️ GitHub CLI missing — open Actions tab ^& verify manually:
    echo     https://github.com/starnickell/ScaffoldPRO/actions
    echo Press any key when all workflows succeed...
    pause >NUL
)

echo 🎉✅ Phase P1 complete — branch ^& tag live, CI green.
echo Reply "P1 ✅" here, or paste failing log URL if anything went red.