function BreakBadge({ status }) {

  const getClassName = () => {

    switch (status) {

      case "OPEN":
        return "badge badge-open"

      case "RESOLVED":
        return "badge badge-resolved"

      default:
        return "badge"
    }
  }

  return (
    <span className={getClassName()}>
      {status}
    </span>
  )
}

export default BreakBadge