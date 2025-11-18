/**
 * Common JavaScript Functions
 * Shared utility functions across all pages
 */

// Toast notifications
const Toast = {
    show: function(type, message) {
        const toastEl = document.getElementById(`${type}Toast`);
        const messageEl = document.getElementById(`${type}ToastMessage`);
        
        if (toastEl && messageEl) {
            messageEl.textContent = message;
            const toast = new bootstrap.Toast(toastEl, {
                autohide: true,
                delay: 3000
            });
            toast.show();
        }
    },
    
    success: function(message) {
        this.show('success', message);
    },
    
    error: function(message) {
        this.show('error', message);
    },
    
    warning: function(message) {
        this.show('warning', message);
    },
    
    info: function(message) {
        this.show('info', message);
    }
};

// Loading overlay
const Loading = {
    show: function(message = 'Loading...') {
        const overlay = document.getElementById('loadingOverlay');
        const messageEl = document.getElementById('loadingMessage');
        
        if (overlay) {
            if (messageEl) {
                messageEl.textContent = message;
            }
            overlay.style.display = 'flex';
        }
    },
    
    hide: function() {
        const overlay = document.getElementById('loadingOverlay');
        if (overlay) {
            overlay.style.display = 'none';
        }
    }
};

// Confirmation modal
const Confirmation = {
    show: function(message, onConfirm) {
        const modal = document.getElementById('confirmationModal');
        const messageEl = document.getElementById('confirmationMessage');
        const confirmBtn = document.getElementById('confirmButton');
        
        if (modal && messageEl && confirmBtn) {
            messageEl.textContent = message;
            
            // Remove old event listeners
            const newConfirmBtn = confirmBtn.cloneNode(true);
            confirmBtn.parentNode.replaceChild(newConfirmBtn, confirmBtn);
            
            // Add new event listener
            newConfirmBtn.addEventListener('click', function() {
                const bsModal = bootstrap.Modal.getInstance(modal);
                if (bsModal) {
                    bsModal.hide();
                }
                if (typeof onConfirm === 'function') {
                    onConfirm();
                }
            });
            
            const bsModal = new bootstrap.Modal(modal);
            bsModal.show();
        }
    }
};

// Video operations
const Video = {
    like: async function(videoId) {
        try {
            Loading.show('Processing like...');
            
            const response = await fetch(`/api/videos/${videoId}/like`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            const result = await response.json();
            
            if (result.success) {
                Toast.success('Video liked successfully!');
            } else {
                Toast.error(result.message || 'Failed to like video');
            }
        } catch (error) {
            console.error('Error liking video:', error);
            Toast.error('An error occurred. Please try again.');
        } finally {
            Loading.hide();
        }
    },
    
    share: function(videoId, videoTitle) {
        const modal = document.getElementById('shareModal');
        const videoIdInput = document.getElementById('shareVideoId');
        
        if (modal && videoIdInput) {
            videoIdInput.value = videoId;
            const bsModal = new bootstrap.Modal(modal);
            bsModal.show();
        }
    },
    
    preview: function(videoId, videoTitle, videoUrl) {
        const modal = document.getElementById('videoPreviewModal');
        const titleEl = document.getElementById('videoPreviewTitle');
        const frameEl = document.getElementById('videoPreviewFrame');
        
        if (modal && titleEl && frameEl) {
            titleEl.textContent = videoTitle;
            frameEl.src = videoUrl;
            
            const bsModal = new bootstrap.Modal(modal);
            bsModal.show();
            
            // Clear iframe when modal closes
            modal.addEventListener('hidden.bs.modal', function() {
                frameEl.src = '';
            });
        }
    }
};

// Form validation
const FormValidator = {
    validateField: function(field) {
        const formGroup = field.closest('.form-group');
        if (!formGroup) return true;
        
        field.setAttribute('data-validated', 'true');
        formGroup.classList.remove('error');
        
        let isValid = true;
        const value = field.value.trim();
        
        if (field.required && !value) {
            isValid = false;
        } else if (field.type === 'email' && value) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            isValid = emailRegex.test(value);
        } else if (field.id === 'password' && value && value.length < 6) {
            isValid = false;
        }
        
        if (!isValid) {
            formGroup.classList.add('error');
        }
        
        return isValid;
    },
    
    validateForm: function(form) {
        const fields = form.querySelectorAll('input[required], textarea[required]');
        let isValid = true;
        
        fields.forEach(field => {
            if (!this.validateField(field)) {
                isValid = false;
            }
        });
        
        return isValid;
    },
    
    setupRealTimeValidation: function(form) {
        const fields = form.querySelectorAll('input, textarea');
        
        fields.forEach(field => {
            field.addEventListener('blur', () => {
                this.validateField(field);
            });
            
            field.addEventListener('input', () => {
                if (field.getAttribute('data-validated') === 'true') {
                    this.validateField(field);
                }
            });
        });
    }
};

// Password toggle
const PasswordToggle = {
    init: function() {
        document.querySelectorAll('.toggle-password').forEach(button => {
            button.addEventListener('click', function() {
                const input = this.closest('div').querySelector('input');
                const icon = this.querySelector('i');
                
                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        });
    }
};

// Share form handler
function initShareForm() {
    const sendBtn = document.getElementById('sendShareBtn');
    const shareForm = document.getElementById('shareForm');
    
    if (sendBtn && shareForm) {
        sendBtn.addEventListener('click', async function() {
            const email = document.getElementById('shareEmail').value;
            const message = document.getElementById('shareMessage').value;
            const videoId = document.getElementById('shareVideoId').value;
            
            if (!email) {
                Toast.error('Please enter an email address');
                return;
            }
            
            try {
                Loading.show('Sending...');
                
                const response = await fetch('/api/videos/share', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        videoId: videoId,
                        email: email,
                        message: message
                    })
                });
                
                const result = await response.json();
                
                if (result.success) {
                    Toast.success('Video shared successfully!');
                    const modal = bootstrap.Modal.getInstance(document.getElementById('shareModal'));
                    modal.hide();
                    shareForm.reset();
                } else {
                    Toast.error(result.message || 'Failed to share video');
                }
            } catch (error) {
                console.error('Error sharing video:', error);
                Toast.error('An error occurred. Please try again.');
            } finally {
                Loading.hide();
            }
        });
    }
}

// Pagination handler
function goToPage(page) {
    console.log('Navigate to page:', page);
    // TODO: Implement actual pagination logic
    Toast.info(`Navigating to page ${page}`);
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    // Initialize password toggle
    PasswordToggle.init();
    
    // Initialize share form
    initShareForm();
    
    // Initialize form validation for forms with class 'needs-validation'
    document.querySelectorAll('form.needs-validation').forEach(form => {
        FormValidator.setupRealTimeValidation(form);
    });
});

// Error handler for fetch requests
async function handleFetchError(response) {
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Request failed');
    }
    return response.json();
}

// Export functions to global scope
window.Toast = Toast;
window.Loading = Loading;
window.Confirmation = Confirmation;
window.Video = Video;
window.FormValidator = FormValidator;
window.goToPage = goToPage;
