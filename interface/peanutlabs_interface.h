/*
 * WARNING: this is an autogenerated file and will be overwritten by
 * the extension interface script.
 */
/**
 * Definitions for functions types passed to/from s3eExt interface
 */
typedef  s3eResult(*openRewardsCenter_t)(const char* user_id);

/**
 * struct that gets filled in by peanutlabsRegister
 */
typedef struct peanutlabsFuncs
{
    openRewardsCenter_t m_openRewardsCenter;
} peanutlabsFuncs;