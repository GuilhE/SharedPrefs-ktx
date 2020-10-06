#!/bin/bash

# The website is built using MkDocs with the Material theme.
# https://squidfunk.github.io/mkdocs-material/
# It requires Python to run.
# Install the packages with the following command:
# pip install mkdocs mkdocs-material

set -ex

# Generate the API docs
./gradlew dokkaDoc

# Dokka filenames like `-http-url/index.md` don't work well with MkDocs <title> tags.
# Assign metadata to the file's first Markdown heading.
# https://www.mkdocs.org/user-guide/writing-your-docs/#meta-data
title_markdown_file() {
  TITLE_PATTERN="s/^[#]+ *(.*)/title: \1 - SharedPrefs-ktx/"
  echo "---"                                                     > "$1.fixed"
  cat $1 | sed -E "$TITLE_PATTERN" | grep "title: " | head -n 1 >> "$1.fixed"
  echo "---"                                                    >> "$1.fixed"
  echo                                                          >> "$1.fixed"
  cat $1                                                        >> "$1.fixed"
  mv "$1.fixed" "$1"
}

set +x
for MARKDOWN_FILE in $(find docs/1.x/ -name '*.md'); do
  echo $MARKDOWN_FILE
  title_markdown_file $MARKDOWN_FILE
done
set -x

# Copy in special files that GitHub wants in the project root.

O1="<a href='./converter-gson'>"
R1="<a href='./usage/converter-gson'>"
O2="<a href='./converter-moshi'>"
R2="<a href='./usage/converter-moshi'>"

sed -i "" "s/${O1}/${R1}/g" README.md
sed -i "" "s/${O2}/${R2}/g" README.md

cat README.md | grep -v 'project website' > docs/index.md
cp CHANGELOG.md docs/changelog.md
cp CONTRIBUTING.md docs/contributing.md
cp CODE_OF_CONDUCT.md docs/conduct.md
mkdir docs/usage/
cp converter-gson/README.md docs/usage/converter-gson.md
cp converter-moshi/README.md docs/usage/converter-moshi.md
