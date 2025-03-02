# STYLES
TITLE_STYLE="\033[1;37m"
SUBTITLE_STYLE="\033[1;4;37m"
ERROR_STYLE="\033[0;31m"
SUCCESS_STYLE="\033[0;32m"
WARNING_STYLE="\033[0;33m"
RESET_STYLE_COMMAND="\033[0m"

# ECHO FUNCTIONS
function echoTitle {
  text="$1"
  boxSize=${#text}
  box=$(for ((i = 1; i <= boxSize + 4; i++)); do echo -n "#"; done)
  echo -e "${TITLE_STYLE}$box${RESET_STYLE_COMMAND}"
  echo -e "${TITLE_STYLE}# $1 #${RESET_STYLE_COMMAND}"
  echo -e "${TITLE_STYLE}$box${RESET_STYLE_COMMAND}"
}

function echoSubtitle {
  echo -e "${SUBTITLE_STYLE}\n# $1${RESET_STYLE_COMMAND}"
}

function echoText {
  echo -e "$1"
}

function echoSuccess {
  echo -e "${SUCCESS_STYLE}🟢 SUCCESS: $1${RESET_STYLE_COMMAND}"
}

function echoError {
  echo -e "${ERROR_STYLE}🔴 ERROR: $1${RESET_STYLE_COMMAND}"
}

function echoWarning {
  echo -e "${WARNING_STYLE}🟡 WARNING: $1${RESET_STYLE_COMMAND}"
}