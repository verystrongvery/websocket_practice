export const formatTimeChat = (dateString: string) => {
  const inputDate = new Date(dateString);
  const options: Intl.DateTimeFormatOptions = { hour: '2-digit', minute: '2-digit', hour12: false };
  const formatter = new Intl.DateTimeFormat('ko-KR', options);
  return formatter.format(inputDate);
};
