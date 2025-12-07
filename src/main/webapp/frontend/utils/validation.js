/**
 * Validates a list of email addresses
 * @param {string|string[]} emails - Single email or array of emails
 * @returns {Object} { isValid: boolean, message: string }
 */
export const isValidEmailList = (emails) => {
  const emailList = Array.isArray(emails) ? emails : [emails];
  
  // Check for empty input
  if (emailList.length === 0 || emailList.some(email => !email || email.trim() === '')) {
    return { isValid: false, message: 'Email list cannot be empty' };
  }

  // Check for duplicate emails (case insensitive)
  const uniqueEmails = new Set();
  const duplicateEmails = [];
  
  for (const email of emailList) {
    const normalizedEmail = email.toLowerCase().trim();
    if (uniqueEmails.has(normalizedEmail)) {
      duplicateEmails.push(email);
    } else {
      uniqueEmails.add(normalizedEmail);
    }
  }
  
  if (duplicateEmails.length > 0) {
    return { 
      isValid: false, 
      message: `Duplicate emails found: ${duplicateEmails.join(', ')}` 
    };
  }

  // Validate email format
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const invalidEmails = emailList.filter(email => !emailRegex.test(email.trim()));
  
  if (invalidEmails.length > 0) {
    return { 
      isValid: false, 
      message: `Invalid email format: ${invalidEmails.join(', ')}` 
    };
  }

  return { isValid: true, message: 'All emails are valid' };
};

/**
 * Validates a single email address
 * @param {string} email - Email address to validate
 * @returns {boolean} True if email is valid
 */
export const isValidEmail = (email) => {
  if (!email || typeof email !== 'string') return false;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email.trim());
};
